import type { FormRules } from 'element-plus'

export class ValidationFactory {
  static createRules(type: string): FormRules {
    switch(type) {
      case 'Coordinate':
        return this.coordinateRules()
      case 'Dragon':
        return this.dragonRules()
      case 'Person':
        return this.personRules()
      default:
        return {}
    }
  }

  private static coordinateRules(): FormRules {
    return {
      x: [
        { required: true, message: 'X is required', trigger: 'blur' },
        { type: 'number', message: 'X must be a number', trigger: 'change' },
        { min: -999, message: 'X must be greater than -999', trigger: 'change' }
      ],
      y: [
        { required: true, message: 'Y is required', trigger: 'blur' },
        { type: 'number', message: 'Y must be a number', trigger: 'change' },
        { max: 844, message: 'Y must be less than 845', trigger: 'change' }
      ]
    }
  }

  private static dragonRules(): FormRules {
    return {
      name: [
        { required: true, message: 'Name is required', trigger: 'blur' },
        { min: 2, max: 50, message: 'Name must be 2-50 characters', trigger: 'blur' }
      ],
      age: [
        { required: true, message: 'Age is required', trigger: 'blur' },
        { type: 'number', message: 'Age must be a number', trigger: 'change' },
        { min: 0, max: 10000, message: 'Age must be between 0 and 10000', trigger: 'change' }
      ]
    }
  }

  private static personRules(): FormRules {
    return {
      name: [
        { required: true, message: 'Name is required', trigger: 'blur' }
      ],
      email: [
        { required: true, message: 'Email is required', trigger: 'blur' },
        { type: 'email', message: 'Please enter valid email', trigger: 'blur' }
      ]
    }
  }
}